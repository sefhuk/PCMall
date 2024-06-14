// 로그아웃
function logout() {
  const isLogoutRequested = confirm("로그아웃 하시겠습니까?");

  if (!isLogoutRequested) {
    return;
  }

  fetch(`${window.location.origin}/logout`, {
    method: "POST",
  })
  .then((res) => {
    location.href = "/";
  })
  .catch((err) => {
    alert("로그아웃 실패");
  });
}
