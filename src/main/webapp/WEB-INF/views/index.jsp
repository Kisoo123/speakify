<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Voice Chat</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<h2>Voice Chat</h2>
<h2 id="test">Click here to start voice chat</h2>

<script>
    const socket = new WebSocket("ws://localhost:8080/signal");
    const peerConnection = new RTCPeerConnection();

    // Media constraints 정의
    const constraints = {
        audio: true,
        video: false  // 비디오 사용하지 않기
    };

    // 미디어 스트림 요청 및 피어 연결에 추가
    function startSignaling() {
        navigator.mediaDevices.getUserMedia(constraints)
            .then(stream => {
                // 스트림의 오디오 트랙을 피어 연결에 추가
                stream.getAudioTracks().forEach(track => peerConnection.addTrack(track, stream));
            })
            .catch(error => console.error('Error accessing media devices.', error));

        peerConnection.createOffer().then(offer => {
            peerConnection.setLocalDescription(offer);
            socket.send(JSON.stringify({
                type: 'offer',
                offer: offer
            }));
        });
    }

    // ICE 후보 처리
    peerConnection.onicecandidate = event => {
        if (event.candidate) {
            socket.send(JSON.stringify({
                type: 'candidate',
                candidate: event.candidate
            }));
        }
    };

    // 원격 스트림 처리
    peerConnection.ontrack = event => {
        const remoteAudio = document.createElement('audio');
        remoteAudio.srcObject = event.streams[0];
        remoteAudio.autoplay = true;
        document.body.appendChild(remoteAudio);
    };

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
            startSignaling();
        });
    });
</script>
</body>

</html>
