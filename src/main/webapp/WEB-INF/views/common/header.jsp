<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>

<header id="header-div">
    <img id="toggle-sidebar" src="${pageContext.request.contextPath}/images/logo/SPEAKIFY_dark_sliced.png">
        <c:if test="${loginMember!='anonymousUser'}">
    <div class="dropdown">
        <i class="bi bi-person" id="profileBt" role="button" data-bs-toggle="dropdown" aria-expanded="false"></i>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileBt">
                <li><a class="dropdown-item" id="profileMenu" href="#">Profile</a></li>
                <li><a class="dropdown-item" href="#">Settings</a></li>
                <li>
                    <hr class="dropdown-divider">
                </li>
                <li><a class="dropdown-item" href="/logout">Logout</a></li>
            </ul>

    </div>
        </c:if>
</header>
<script>
    $(document).ready(function() {
        // 프로필 메뉴 클릭 시
        $('#profileMenu').click(function() {
            // 프로필 수정 폼 HTML 생성
            const profileForm = `
                <div class="container mt-4" id="profile-form-container">
                    <div class="card">
                        <div class="card-header">
                            Edit Profile
                            <c:out value="${loginMember}"></c:out>
                        </div>
                        <div class="card-body">
                            <form>
                                <!-- 프로필 사진 영역 -->
                                <div class="mb-3 text-center">
                                    <img id="profileImage" src="${path}/images/icon/${loginMember.profilePictureUrl}" alt="Profile Picture" class="rounded-circle" width="150" height="150" style="background-color:grey;">
                                </div>
                                <div style="display:flex; align-items:center; flex-direction:column">
                                    <!-- 프로필 사진 변경 -->
                                    <div class="mb-3">
                                        <label for="profilePicture" class="form-label">Change Profile Picture</label>
                                        <input type="file" class="form-control" id="profilePicture" accept="image/*">
                                    </div>

                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>
                                        <input type="text" class="form-control" id="username" placeholder="${loginMember.displayName}">
                                    </div>
                                    <div class="mb-3">
                                        <label for="statusMessage" class="form-label">Status Message</label>
                                        <textarea class="form-control" id="statusMessage" rows="3" placeholder="Enter your status message"></textarea>
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
            $('#cancelProfileEdit').click(function() {
                $('#profile-form-container').remove(); // 폼 전체를 제거
            });
        });
    });
</script>