<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);
%>
<!DOCTYPE html>
<html lang="en">
<c:set var="path" value="${pageContext.request.contextPath}"/>


<head>
    <meta charset="UTF-8">
    <title>SPEAKIFY</title>
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${path}/css/index.css?ver=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" type="text/css" href="${path}/css/header.css?ver=<%= System.currentTimeMillis()%>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
<c:import url="${path}/WEB-INF/views/common/header.jsp"/>

<div id="root-div">
    <%-- 사이드바 --%>
    <div id="sidebar">
        <div id="innder-sidebar">
            <div id="contactButton">
                <img class="sidebar-icons" src="${path}/images/icon/user-icon-white.png">
                <img class="sidebar-icons" src="${path}/images/icon/users-icon-white.png">
                <img class="sidebar-icons" src="${path}/images/icon/search-icon-white.png">
            </div>
        </div>
    </div>
    <div id="header-main-div">

        <div id="main-div">
            <p>principal : <sec:authentication property="principal"/></p>
            <p>principal : <sec:authentication property="authorities"/></p>
            <h2 id="test">Click here to start voice chat</h2>
            <a href="/login">dasdsa</a>
        </div>
        <sec:authorize access="isAnonymous()">

            <div id="login-form-div">

                <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" action="${path}/login" method="post">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="floatingInput" placeholder="Username"
                               name="username">
                        <label for="floatingInput">Username</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                               name="password">
                        <label for="floatingPassword">Password</label>
                    </div>
                    <div class="checkbox mb-3">
                        <label>
                            <input type="checkbox" value="remember-me" name="remember-me"> Remember me
                        </label>
                    </div>
                    <button class="w-100 btn btn-lg btn-dark" type="submit">Sign in</button>
                    <div id="find-accountment">
                        <button type="button" class="btn btn-link">아이디찾기</button>
                        <button type="button" class="btn btn-link">비밀번호찾기</button>
                        <button type="button" class="btn btn-link" onclick="register()">회원가입</button>
                    </div>
                    <hr class="my-3">
                    <small class="text-body-secondary">By clicking Sign up, you agree to the terms of use.</small>
                </form>
            </div>
        </sec:authorize>
    </div>

</div>


</body>

<script>
    //로그인 관련
    let register = () => {
        window.location.href = '${path}/register';
    }
    // 사이드바
    document.getElementById("toggle-sidebar").addEventListener("click", function () {
        document.body.classList.toggle("sidebar-open");
    });

    // 음성채팅 관련
    const contextPath = '<%= request.getContextPath() %>';
    let socket = new WebSocket(`ws://${location.host}${contextPath}/signal`);
    let peerConnection = new RTCPeerConnection();

    // 웹소켓 재연결 메소드
    function initializeWebSocket() {
        if (socket.readyState === WebSocket.CLOSED || socket.readyState === WebSocket.CLOSING) {
            console.log("WebSocket is closed. Reconnecting...");
            socket = new WebSocket(`wss://${location.host}${contextPath}/signal`);
        }
    }

    // Media constraints 정의
    const constraints = {
        audio: true
    };

    // 미디어 스트림 요청 및 피어 연결에 추가
    function startSignaling() {
        // 현재 연결 상태 확인
        if (peerConnection.signalingState !== "stable") {
            console.log("Connection is not stable, offer creation aborted.");
            return; // 이미 offer가 진행 중일 때 중복 생성 방지
        }

        navigator.mediaDevices.getUserMedia({audio: true})
            .then(stream => {
                console.log("Local audio stream added:", stream);
                stream.getAudioTracks().forEach(track => peerConnection.addTrack(track, stream));
            })
            .catch(error => {
                console.error('Error accessing media devices:', error);
            });


        peerConnection.createOffer().then(offer => {
            console.log("Created offer:", offer);
            return peerConnection.setLocalDescription(offer);
        }).then(() => {
            socket.send(JSON.stringify({
                type: 'offer',
                offer: peerConnection.localDescription
            }));
        }).catch(error => console.error("Error creating offer:", error));
    }

    // ICE 후보 수집과 원격 스트림 처리 설정 함수
    function setupPeerConnectionHandlers(peerConnection) {
        // ICE 후보 수집
        peerConnection.onicecandidate = event => {
            if (event.candidate) {
                console.log("Collected ICE candidate:", event.candidate);
                socket.send(JSON.stringify({
                    type: 'candidate',
                    candidate: event.candidate
                }));
            } else {
                console.log("All ICE candidates have been sent.");
            }
        };

        // 원격 스트림 처리
        peerConnection.ontrack = event => {
            const remoteAudio = document.createElement('audio');
            remoteAudio.srcObject = event.streams[0];
            remoteAudio.autoplay = true;
            document.body.appendChild(remoteAudio);
        };

        // 연결 상태 변경 시
        peerConnection.oniceconnectionstatechange = () => {
            console.log("ICE Connection State: ", peerConnection.iceConnectionState);
            if (peerConnection.iceConnectionState === 'failed' || peerConnection.iceConnectionState === 'disconnected') {
                console.log("Recreating peer connection...");
                recreatePeerConnection();
            }
        };
    }

    function recreatePeerConnection() {
        if (peerConnection) {
            peerConnection.close();
        }

        peerConnection = new RTCPeerConnection();

        // ICE 후보 수집과 원격 스트림 처리 설정
        setupPeerConnectionHandlers(peerConnection);
    }

    // 소켓 메시지 처리
    socket.onmessage = message => {
        const data = JSON.parse(message.data);

        if (data.type === 'offer') {
            peerConnection.setRemoteDescription(new RTCSessionDescription(data.offer));
            peerConnection.createAnswer().then(answer => {
                peerConnection.setLocalDescription(answer);
                socket.send(JSON.stringify({
                    type: 'answer',
                    answer: answer
                }));
            });
        } else if (data.type === 'answer') {
            peerConnection.setRemoteDescription(new RTCSessionDescription(data.answer));
        } else if (data.type === 'candidate') {
            peerConnection.addIceCandidate(new RTCIceCandidate(data.candidate));
        }
    };

    $(document).ready(function () {
        $("#test").click(function () {
            initializeWebSocket();
            startSignaling();
            setupPeerConnectionHandlers(peerConnection); // 피어 연결 핸들러 설정
        });
    });
</script>

</html>
