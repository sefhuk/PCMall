function sendVerificationCode() {
    const email = document.getElementById('email').value;
    alert('인증 코드가 ' + email + '로 전송되었습니다.');
    document.querySelector('.verify-email').style.display = 'flex';
}

function verifyCode() {
    const code = document.getElementById('verification-code').value;
    if (code === '123456') {
        alert('이메일 인증이 완료되었습니다.');
    } else {
        alert('인증 코드가 잘못되었습니다.');
    }
}