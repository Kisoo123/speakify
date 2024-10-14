<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<sec:authentication var="loginMember" property="principal"/>

<header id="header-div">
        <img id="toggle-sidebar" src="${pageContext.request.contextPath}/images/logo/SPEAKIFY_dark_sliced.png">

    <c:if test="${loginMember!='anonymousUser'}">
        <div class="profile" id="profileContainer">
            <div id="profile-data">
                <div id="profile-displayName">
                    <c:out value="${loginMember.displayName}"></c:out>
                </div>

                <div id="profile-uniqueTag">
                    <c:out value="#${loginMember.uniqueTag}"></c:out>
                </div>
            </div>
            <div class="dropdown" id="profileDropdown">
                 <img id="profileBt"
                 src="https://speakifybucket.s3.amazonaws.com/uploads/public/profile/${loginMember.id}${loginMember.profilePictureUrl}"
                 alt="Profile Picture"
                 class="rounded-circle"
                 style="background-color:grey;"
                 role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileBt">
                    <li><a class="dropdown-item" id="profileMenu" href="#">Profile</a></li>
                    <li><a class="dropdown-item" href="#">Settings</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item" href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </c:if>
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>

</header>
<c:if test="${loginMember!='anonymousUser'}">
    <script>

        $(document).ready(function () {
            // 동적으로 추가된 input 요소에 대해 이벤트 위임 사용
            $(document).on('change', '#profilePicture', function (event) {
                console.log('파일 선택됨'); // 콘솔 로그로 확인
                const file = event.target.files[0];

                if (file) {
                    const reader = new FileReader();

                    reader.onload = function (e) {
                        // img 태그의 src 속성을 선택한 파일로 변경
                        $('#profileImage').attr('src', e.target.result).show();
                    };

                    reader.readAsDataURL(file); // 파일을 읽어서 base64로 변환
                }
            });
            // 프로필 메뉴 클릭 시
            $('#profileMenu').click(function () {
                // 프로필 수정 폼 HTML 생성
                const profileForm = `
                <div class="container mt-4" id="profile-form-container">
                    <div class="card">
                        <div class="card-header">
                            Edit Profile
                            <c:out value="${loginMember}"></c:out>
                        </div>
                        <div class="card-body">
                            <form action="${path}/updateProfile" method="post" enctype="multipart/form-data">
                                <!-- 프로필 사진 영역 -->
                                <div class="mb-3 text-center">
                                    <img id="profileImage" src="https://speakifybucket.s3.amazonaws.com/uploads/public/profile/${loginMember.id}${loginMember.profilePictureUrl}" alt="Profile Picture" class="rounded-circle" width="150" height="150" style="background-color:grey;">
                                </div>
                                <div style="display:flex; align-items:center; flex-direction:column">
                                    <!-- 프로필 사진 변경 -->
                                    <div class="mb-3">
                                        <label for="profilePicture" class="form-label">Change Profile Picture</label>
                                        <input type="file" class="form-control" id="profilePicture" name="profilePicture" accept="image/*">
                                    </div>

                                    <div class="mb-3">
                                        <label for="displayName" class="form-label">Username</label>
                                        <input type="text" class="form-control" id="displayName" name="displayName" placeholder="${loginMember.displayName}">
                                    </div>
                                    <div class="mb-3">
                                        <label for="statusMessage" class="form-label">Status Message</label>
                                        <textarea class="form-control" id="statusMessage" name="statusMessage" rows="3" placeholder="Enter your status message"></textarea>
                                    </div>
                                </div>
                                <div style="display:flex;justify-content:center">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <button type="button" class="btn btn-secondary" id="cancelProfileEdit">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            `;


                // header-main-div에 폼 추가
                $('#header-main-div').empty(); // 기존 내용을 지우고
                $('#header-main-div').append(profileForm); // 새 폼을 추가

                // 취소 버튼 클릭 시 폼 제거
                $('#cancelProfileEdit').click(function () {
                    $('#profile-form-container').remove(); // 폼 전체를 제거
                });
            });
        });
    </script>

</c:if>