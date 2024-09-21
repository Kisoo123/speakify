<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Voice Chat</title>
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<h2>Voice Chat</h2>
<h2 id="test">Click here to start voice chat</h2>
<label for="userId">User ID:</label>
<input type="text" id="userId" placeholder="Enter your ID">
<button id="connect">Connect</button>
<a href="/login">dasdsa</a>

<script>
    const contextPath = '<%= request.getContextPath() %>';
    let socket = new WebSocket(`wss://${location.host}${contextPath}/signal`);
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

        navigator.mediaDevices.getUserMedia({ audio: true })
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

    $(document).ready(function(){
        $("#test").click(function(){
            initializeWebSocket();
            startSignaling();
            setupPeerConnectionHandlers(peerConnection); // 피어 연결 핸들러 설정
        });
    });
</script>
</body>

</html>
