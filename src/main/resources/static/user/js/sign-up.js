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

//function validateField(field, errorMessage) {
//    if (field.validity.valid) {
//        field.nextElementSibling.textContent = '';
//        field.classList.remove('invalid');
//    } else {
//        field.nextElementSibling.textContent = errorMessage;
//        field.classList.add('invalid');
//    }
//}

//document.addEventListener('DOMContentLoaded', function () {
//    const nameField = document.querySelector('input[name="name"]');
//    const emailField = document.querySelector('input[name="email"]');
//    const passwordField = document.querySelector('input[name="password"]');
//    const confirmPasswordField = document.querySelector('input[name="confirmPassword"]');
//    const phoneField = document.querySelector('input[name="phoneNumber"]');
//
//    nameField.addEventListener('input', function () {
//        validateField(nameField, '이름은 최소 2자 이상이어야 합니다.');
//    });
//
//    emailField.addEventListener('input', function () {
//        validateField(emailField, '유효한 이메일 주소를 입력해 주세요.');
//    });
//
//    passwordField.addEventListener('input', function () {
//        validateField(passwordField, '비밀번호는 최소 8자 이상이어야 합니다.');
//    });
//
//    confirmPasswordField.addEventListener('input', function () {
//        if (confirmPasswordField.value === passwordField.value) {
//            confirmPasswordField.nextElementSibling.textContent = '';
//            confirmPasswordField.classList.remove('invalid');
//        } else {
//            confirmPasswordField.nextElementSibling.textContent = '비밀번호가 일치하지 않습니다.';
//            confirmPasswordField.classList.add('invalid');
//        }
//    });
//
//    phoneField.addEventListener('input', function () {
//        const phonePattern = /^\d{10,11}$/;
//        if (phonePattern.test(phoneField.value)) {
//            phoneField.nextElementSibling.textContent = '';
//            phoneField.classList.remove('invalid');
//        } else {
//            phoneField.nextElementSibling.textContent = '유효한 전화번호를 입력해 주세요.';
//            phoneField.classList.add('invalid');
//        }
//    });
//});
