




//function toggleEdit(field) {
//    const displaySpan = document.getElementById(`${field}-display`);
//    const editInput = document.getElementById(`${field}-edit`);
//    const saveButton = document.getElementById(`${field}-save`);
//
//    if (editInput.style.display === 'none') {
//        displaySpan.style.display = 'none';
//        editInput.style.display = 'block';
//        saveButton.style.display = 'inline-block';
//    } else {
//        displaySpan.style.display = 'inline';
//        editInput.style.display = 'none';
//        saveButton.style.display = 'none';
//    }
//}
//
//function saveChanges(field) {
//    const displaySpan = document.getElementById(`${field}-display`);
//    const editInput = document.getElementById(`${field}-edit`);
//
//    displaySpan.textContent = editInput.value; // 변경된 값을 표시
//    toggleEdit(field);
//}