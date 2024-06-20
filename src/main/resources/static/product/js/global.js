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

const inputs = document.getElementsByTagName("input");

for (let i = 0; i < inputs.length; i++) {
  const inputType = inputs[i].type;

  if (inputType === "number") {
    inputs[i].addEventListener("change", (e) => {
      let value = e.target.value.replace(/^0+(?=\d)/, '');

      // 소수점이 있는 경우 처리
      if (value.includes('.')) {
        // 소수점 뒤의 0 제거
        value = value.replace(/\.?0+$/, '');
      }

      if (value === '0') {
        value = null;
      }

      inputs[i].value = value;
    });
  }
}