function logout() {
    // 로그아웃 로직을 여기에 추가
    location.href = '/logout';
}

function deleteAccount() {
    // 회원 탈퇴 로직을 여기에 추가
    if (confirm("정말 회원 탈퇴하시겠습니까?")) {
        location.href = '/delete-account';
    }
}
