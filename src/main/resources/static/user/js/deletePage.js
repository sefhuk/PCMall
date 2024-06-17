function deleteUser() {
    fetch('/deleteUser', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            alert('회원정보 삭제에 성공했습니다.');
            window.location.href = '/logout';
        } else {
            alert('회원정보 삭제에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('회원정보 삭제 중 오류가 발생했습니다.');
    });
}