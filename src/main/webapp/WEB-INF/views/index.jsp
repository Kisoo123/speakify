<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" pageEncoding="utf-8" %>
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
<sec:authentication var="loginMember" property="principal"/>

<head>
    <meta charset="UTF-8">
    <title>SPEAKIFY</title>
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://cdn.jsdelivr.net/sockjs/1.0.2/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${path}/css/index.css?ver=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" type="text/css" href="${path}/css/header.css?ver=<%= System.currentTimeMillis()%>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<div id="root-div">
    <%-- 사이드바 --%>
    <div id="sidebar">
        <div id="innder-sidebar">
            <div id="contactButton">
                <img class="sidebar-icons" id="showFriends" src="${path}/images/icon/user-icon-white.png">
                <img class="sidebar-icons" src="${path}/images/icon/users-icon-white.png">
                <img class="sidebar-icons" id="toggle-search" src="${path}/images/icon/search-icon-white.png">
                <svg class="sidebar-icons-bell bi bi-bell" id="alarm" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"  viewBox="0 0 16 16">
                    <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2M8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6"></path>
                </svg>
            </div>
        </div>
        <div id="search-input-container">
            <input type="text" id="search-input" placeholder="유저이름 검색" style="display:none;">
        </div>
        <div id="search-result"></div>

    </div>
    <div id="header-main-div">

        <div id="main-div">
            <p><c:out value="${loginMember}"> </c:out></p>
            <p><c:out value="${loginMember != 'anonymousUser'}"> </c:out></p>

            <p>principal : <sec:authentication property="principal"/></p>
            <p>principal : <sec:authentication property="authorities"/></p>
            <h2 id="test">Click here to start voice chat</h2>
            <a href="/login">dasdsa</a>
        </div>
        <!-- Toast 경고창 (숨겨져 있다가 JavaScript로 보여줍니다) -->
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="alarmToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <strong class="me-auto">알림</strong>
                    <small>지금</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    요청이 처리되었습니다.
                </div>
            </div>
        </div>


        <%--    로그인 창    --%>
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

    /* 로그인 관련 */
    // 클릭하면 회원가입창으로 이동
    let register = () => {
        window.location.href = '${path}/register';
    }
</script>

<c:if test="${loginMember!='anonymousUser'}">
<%--    로그인 후 실행되는 스크립트--%>


    <script>
        <%--                    알람관련                --%>

        // 유저의 ID, 서버에서 이 값을 통해 해당 유저의 알림을 구독

        // SockJS를 이용해 WebSocket 연결을 설정
        const Stompsocket = new SockJS('http://' + window.location.host + '/ws'); // 서버의 WebSocket 엔드포인트
        const stompClient = Stomp.over(Stompsocket);

        // 연결을 성공하면 구독을 설정
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // /topic/user/{userId} 경로를 구독
            stompClient.subscribe('/topic/user/' + '${loginMember.id}', function (message) {
                // 서버로부터 알림이 도착했을 때 처리하는 로직
                const friendId = message.body;
                alert('친구 요청이 왔습니다. 친구 ID: ' + friendId);
            });
        });


        // 음성채팅 관련
        let socket = new WebSocket('wss://' + window.location.host + '/signal');
        let peerConnection = new RTCPeerConnection();

        // 웹소켓 재연결 메소드
        function initializeWebSocket() {
            if (socket.readyState === WebSocket.CLOSED || socket.readyState === WebSocket.CLOSING) {
                console.log("WebSocket is closed. Reconnecting...");
                socket = new WebSocket('wss://' + window.location.host + '/signal');
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

        $("#test").click(function () {
            initializeWebSocket();
            startSignaling();
            setupPeerConnectionHandlers(peerConnection); // 피어 연결 핸들러 설정
        });

        /* 사이드바 */
        // 사이드바 띄우기
        $("#toggle-sidebar").click(function () {
            $("body").toggleClass("sidebar-open");
        });
        //사이드바 검색 아이콘 클릭
        // search 아이콘 클릭 시 input을 표시하거나 숨김
        $("#toggle-search").click(function () {
            $('#search-result').empty();
            $("#search-input").toggle(); // input 태그의 표시/숨김을 토글
            $("#search-input").focus(); // input에 포커스를 줌
        });
        // 알람 아이콘 클릭 시
        $("#alarm").click(function () {
            $("#search-input").hide();
            $.ajax({
                type: 'post',
                url: '/findAlarm', // 알람 가져오는 URL
                data: {
                    'userId': '${loginMember.id}' // 로그인된 유저 ID
                },
                success: function (response) {
                    // 먼저 기존의 알람 목록을 초기화
                    $('#search-result').empty();

                    // 알람 데이터가 있으면 이를 HTML로 변환하여 추가
                    if (response.length > 0) {
                        let alarmElement = null;
                        response.forEach(alarm => {
                            if (alarm.type == 'friendRequest') {
                                alarmElement = `
                                    <div class="list-group-item list-group-item-action py-2 lh-tight bg-dark" data-alarm-id="\${alarm.id}">
                                        <div class="d-flex w-100 align-items-center justify-content-between">
                                            <small class="text-muted">\${alarm.createAt}</small>
                                        </div>
                                        <p class="text-light">\${alarm.display_name}의 친구 요청.</p>
                                        <div class="alarm-buttons" style="display:flex;justify-content: space-around;">
                                            <button class="action-btn btn btn-success" data-user-id="\${alarm.usrId}" data-alarm-id ="\${alarm.friendRequestId}" data-action="accept">수락</button>
                                            <button class="action-btn btn btn-danger" data-user-id="\${alarm.usrId}" data-alarm-id ="\${alarm.friendRequestId}" data-action="reject">거절</button>
                                        </div>
                                    </div>
                                `;

                            }
                            $('#search-result').append(alarmElement);
                        });
                    } else {
                        // 알람이 없을 때 메시지 표시
                        $('#search-result').append('<p class="text-muted">알람이 없습니다.</p>');
                    }
                },
                error: function () {
                    console.log('오류 발생');
                }
            });
        });
        //친구신청 수락||거절
        $(document).on('click', '.action-btn', function () {
            const userId = $(this).data('user-id'); // 알람 ID
            const action = $(this).data('action'); // 수락 or 거절
            const alarmId = $(this).data('alarmId'); // 수락 or 거절

            // AJAX 요청으로 서버에 알람 ID와 동작(수락/거절)을 전송
            $.ajax({
                type: 'POST',
                url: '/handleFriendRequest', // 알람 처리하는 URL
                data: {
                    userId: userId,
                    action: action,
                    alarmId: alarmId
                },
                success: function (response) {
                    if (response === 'success') {
                        // Bootstrap Toast 경고창을 띄운다
                        $('#alarmToast .toast-body').text(`알람 \${action == 'accept' ? '수락' : '거절'} 완료`);
                        const toast = new bootstrap.Toast($('#alarmToast')); // Bootstrap Toast 객체 생성
                        toast.show(); // 경고창 보여주기

                        // 알람을 UI에서 제거할 수 있습니다.
                        $(`[data-alarm-id='\${alarmId}']`).remove();
                    } else {
                        $('#alarmToast .toast-body').text('알람 처리 중 오류가 발생했습니다.');
                        const toast = new bootstrap.Toast($('#alarmToast'));
                        toast.show();
                    }
                },
                error: function () {
                    $('#alarmToast .toast-body').text('서버 요청 중 오류 발생');
                    const toast = new bootstrap.Toast($('#alarmToast'));
                    toast.show();
                }
            });
        });


        // 유저 검색

        // 검색창에 입력할 때마다 실시간으로 결과 처리
        $('#search-input').on('keyup', function () {
            let query = $(this).val(); // 입력된 검색어 가져오기

            // 입력값이 없으면 검색 결과 초기화
            if (query.length === 0) {
                $('#search-result').empty();
                return;
            }

            // AJAX 요청으로 서버에 검색 요청
            $.ajax({
                url: '/search', // 검색을 처리할 서버의 엔드포인트
                method: 'POST',
                data: {keyword: query}, // 검색어를 쿼리로 전달
                success: function (response) {
                    console.log(response);
                    console.log("반환됨");
                    $('#search-result').empty(); // 검색 결과 초기화
                    console.log()
                    // 서버에서 받은 데이터를 처리해서 결과를 동적으로 추가
                    response.forEach(user => {
                        let userElement =
                            `<div class="dropdown">
                                <a class="list-group-item list-group-item-action py-2 lh-tight bg-dark" href="#" role="button" id="dropdownMenuLink\${user.id}" data-bs-toggle="dropdown" aria-expanded="false">
                                <div class="d-flex w-100 align-items-center justify-content-between">
                                    <strong class="mb-1">\${user.display_name}</strong>
                                    <small class="text-muted">\${user.statusMessage}</small>
                                </div>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="dropdownMenuLink\${user.id}">
                                    <li><a class="dropdown-item">프로필 보기</a></li>
                                    <li><a id="friend-request" class="dropdown-item" data-user-id="\${user.id}">친구 신청</a></li>
                                </ul>
                            </div>`;
                        $('#search-result').append(userElement);
                    });

                    // 검색 결과가 없을 경우 메시지 출력
                    if (response.length === 0) {
                        $('#search-result').append('<p class="text-muted">검색 결과가 없습니다.</p>');
                    }
                },
                error: function () {
                    $('#search-result').html('<p class="text-danger">검색 중 오류가 발생했습니다.</p>');
                }
            });
        });

        //친구신청
        $(document).on('click', '#friend-request', function () {
            const friendId = $(this).data('user-id'); // 친구 신청 대상의 ID 가져오기

            $.ajax({
                type: 'GET',
                url: '/friendRequest', // 요청을 보낼 URL
                data: {toUser: friendId}, // 친구 ID를 데이터로 전송
                success: function (response) {
                    console.log(response);
                    switch (response) {
                        case 'success':
                            alert('친구 요청이 전송되었습니다.');
                            break;
                        case 'myPending':
                            alert('이미 요청을 보냈습니다');
                            break;
                        case 'friendPending':
                            alert('상대가 요청을 이미 보냈습니다');
                        case 'alreadyFriend':
                            alert('이미 친구입니다')
                            break;

                    }
                    // 추가적인 성공 처리 로직
                },
                error: function (xhr, status, error) {
                    alert('친구 요청 전송에 실패했습니다: ' + error);
                    // 오류 처리 로직
                }
            });
        });
        // 친구 아이콘 클릭 시 친구 목록 불러오기
        $('#showFriends').click(function () {
            $("#search-input").hide();
            $.ajax({
                type: 'POST', // 서버에 GET 요청을 보냄
                url: '/getFriendList', // 서버의 친구 목록을 불러오는 URL
                data: {
                    'userId': '${loginMember.id}' // 로그인된 유저 ID 전송
                },
                success: function (response) {
                    console.log(response);
                    // 먼저 search-result의 내용을 초기화
                    $('#search-result').empty();

                    // 친구 목록이 있을 경우
                    if (response.length > 0) {
                        let friendListHtml = ''; // 친구 목록 HTML을 담을 변수

                        // 응답으로 받은 친구 데이터를 순회하며 HTML을 생성
                        response.forEach(friend => {
                            console.log(friend);
                            friendListHtml += `
                                <div class="friend-item dropdown" id="friend-\${friend.usrId}">
                                <p class="dropdown-toggle" id="dropdownMenuButton\${friend.usrId}" data-bs-toggle="dropdown" aria-expanded="false">
                                \${friend.display_name} (\${friend.statusMessage || ''})
                        </p>
                            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="dropdownMenuButton\${friend.usrId}">
                                <li><a class="dropdown-item" href="#" onclick="viewProfile(\${friend.id})">프로필 보기</a></li>
                                <li><a class="dropdown-item" href="#" onclick="startCall(\${friend.usrId},'\${friend.display_name}')">통화하기</a></li>
                            </ul>
                        </div>
                            `;
                            ;
                        });

                        // 결과를 search-result에 추가
                        $('#search-result').append(friendListHtml);
                    } else {
                        // 친구 목록이 없을 때 메시지 표시
                        $('#search-result').append('<p class="text-muted">친구 목록이 없습니다.</p>');
                    }
                },
                error: function () {
                    $('#search-result').html('<p class="text-danger">친구 목록을 가져오는 중 오류가 발생했습니다.</p>');
                }
            });
        });

        // 프로필 보기 함수
        function viewProfile(friendId) {
            // 프로필을 보여주는 로직을 구현
            alert('프로필 보기: ' + friendId);
        }

        // 통화하기 함수
            var roomNo = null;
        function startCall(friendId,displayName) {
            console.log('친구아이디'+friendId);
            // 기존 채팅방이 있을 경우 제거
            $('#chat-room').remove();
            // 새로운 채팅방 div 추가
            let chatRoomHtml = `
        <div id="chat-room" class="chat-room">
            <h3>\${displayName}와의 통화 및 채팅</h3>
            <div class="chat-controls">
                <button class="btn btn-danger" onclick="endCall()">통화 종료</button>
            </div>
            <div class="chat-box">
                <div id="messages" class="messages" style="height: 65vh;">
                    <!-- 여기에 채팅 메시지가 추가됩니다. -->
                </div>
                <div class="chat-input">
                    <input type="text" id="messageInput" class="form-control" placeholder="메시지를 입력하세요">
                    <button class="btn btn-primary" onclick="sendMessage(\${friendId})">보내기</button>
                </div>
            </div>
        </div>
    `;
            $.ajax({
                type:"POST",
                url:"/startCall",
                data:{
                    friendId : friendId,
                    myId : ${loginMember.id}
                },
                success:function(response){
                    console.log(response.roomNo+"방번호");
                    roomNo = response.roomNo;
                    let messages = response.messages;
                    // 받은 메시지를 #messages div에 추가
                    messages.forEach(msg => {
                        let messageHtml = `
                            <div class="message-item">
                                <strong>\${msg.writer === response.myId ? '나' : '상대방'}: </strong>
                                <span>\${msg.message}</span>
                                <small class="text-muted">\${new Date(msg.messageTime).toLocaleTimeString()}</small>
                            </div>
                        `;
                        $("#messages").append(messageHtml);
                    });
                        stompClient.subscribe('/topic/room/' + roomNo, function (message) {
                            let loginMemberId = ${loginMember.id};
                            let parsedMessage = JSON.parse(message.body);

                            console.log('sub수신');
                            console.log(message);
                            let messageHtmlLive = `
                            <div class="message-item">
                                <strong>\${parsedMessage.writer === loginMemberId ? '나' : '상대방'}: </strong>
                                <span>\${parsedMessage.message}</span>
                                <small class="text-muted">\${new Date(parsedMessage.messageTime).toLocaleTimeString()}</small>
                            </div>
                        `;
                            $("#messages").append(messageHtmlLive);
                        });
                },error:function(error){
                    console.log(error);
                }
            });

            // #header-main-div 안에 채팅방 추가
            $('#header-main-div').append(chatRoomHtml);

            // 통화 시작 로직을 여기에 추가
            console.log('통화 시작: ' + friendId);
        }

        // 통화 종료 함수
        function endCall() {
            // 통화 종료 처리 및 채팅방 제거
            $('#chat-room').remove();
            alert('통화가 종료되었습니다.');
        }

        // 메시지 보내기 함수
        function sendMessage(friendId) {
            const message = $('#messageInput').val();
            if (message.trim() === '') {
                alert('메시지를 입력하세요.');
                return;
            }

            // 메시지를 서버로 전송하는 로직 (추후 서버 연동 필요)
            console.log('메시지 전송:', message, '대상:', friendId);
            console.log('방번호'+roomNo);
            $.ajax({
                type:"POST",
                url:"/sendMessage",
                contentType: "application/json",
                data:JSON.stringify({
                    channelId : roomNo,
                    writer : ${loginMember.id},
                    message : message,
                    messageTime : new Date().getTime()
                }),
                success:function (response){
                    console.log(response);
                }
            })

            // 전송한 메시지를 채팅창에 추가
            $('#messages').append(`<div class="sent-message"><strong>나: </strong>\${message}</div>`);
            $('#messageInput').val(''); // 입력창 비우기

        }

    </script>
</c:if>

</html>
