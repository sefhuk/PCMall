function loadManufacturers() {
  const select = document.getElementById('categorySelect');
  const parentId = select.value;

  fetch(`/categories/${parentId}/manufacturers`)
  .then(response => response.json())
  .then(manufacturers => {
    const table = document.getElementById('categoryTable');
    table.innerHTML = '';
    manufacturers.forEach(manufacturer => {
      const newRow = table.insertRow();
      newRow.dataset.id = manufacturer.id;
      const nameCell = newRow.insertCell(0);
      const editCell = newRow.insertCell(1);
      const deleteCell = newRow.insertCell(2);

      nameCell.textContent = manufacturer.name;
      editCell.innerHTML = '<button onclick="editManufacturer(this)" class="edit-btn">수정</button>';
      deleteCell.innerHTML = '<button onclick="deleteManufacturer(this)" class="delete-btn">삭제</button>';
    });
  });
}

function loadDefaultManufacturers() {
  const select = document.getElementById('categorySelect');
  if (select.value) {
    loadManufacturers();
  }
}

function editManufacturer(button) {
  const row = button.closest('tr');
  const id = row.dataset.id;
  const name = prompt('새 이름을 입력하세요:', row.cells[0].textContent);

  if (name) {
    fetch('/categories/edit', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({ id, name })
    })
    .then(response => response.json())
    .then(data => {
      row.cells[0].textContent = data.name;
    });
  }
}

function deleteManufacturer(button) {
  const row = button.closest('tr');
  const id = row.dataset.id;

  if (confirm('정말 삭제하시겠습니까?')) {
    fetch('/categories/delete', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({ id })
    })
    .then(() => {
      row.remove();
    });
  }
}


function addManufacturer() {
  const input = document.getElementById('newManufacturer');
  const name = input.value.trim();
  const select = document.getElementById('categorySelect');
  const parentId = select.value;
  if (name) {
    fetch('/categories/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({ name, parentId })
    })
    .then(response => response.json())
    .then(data => {
      const table = document.getElementById('categoryTable');
      const newRow = table.insertRow();
      newRow.dataset.id = data.id;
      const nameCell = newRow.insertCell(0);
      const editCell = newRow.insertCell(1);
      const deleteCell = newRow.insertCell(2);

      nameCell.textContent = data.name;
      editCell.innerHTML = '<button onclick="editManufacturer(this)" class="edit-btn">수정</button>';
      deleteCell.innerHTML = '<button onclick="deleteManufacturer(this)" class="delete-btn">삭제</button>';

      input.value = '';
    });
  }
}
