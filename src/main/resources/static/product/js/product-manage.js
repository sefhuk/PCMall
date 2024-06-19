const selected = document.getElementsByClassName("selected");
const previewImages = document.getElementsByClassName("preview-image");
const imgDeleteBtns = document.getElementsByClassName("img-delete");
const imageCount = document.getElementById("image-count");
const descInputs = document.getElementsByClassName("descInput");
const images = []; // 미리보기 이미지 리스트

// 상품 설명 폼 데이터
const part = document.getElementById("part");
const brand = document.getElementById("brand");
const name = document.getElementById("name");
const stock = document.getElementById("stock");
const price = document.getElementById("price");

// option 태그 selected 속성을 수정 전 데이터에 맞게 조정
for (let i = 0; i < selected.length; i++) {
  const options = selected[i].getElementsByTagName("option");
  for (let j = 0; j < options.length; j++) {
    if (selected[i].getAttribute("defaultValue") === options[j].value) {
      options[j].selected = true;
    }
  }
}

// 미리보기 이미지를 images 리스트에 초기화
for (let i = 0; i < previewImages.length; i++) {
  const name = previewImages[i].getAttribute("imageName");

  const file = new File([], name, {type: "image/name"})

  images.push(file);
}

for (let i = 0; i < imgDeleteBtns.length; i++) {
  imgDeleteBtns[i].addEventListener('click', function () {
    // 등록된 이미지 src 속성 값
    const imgSrc = this.parentNode.getElementsByTagName('img')[0].src;

    // 이미지 목록 리스트에서 제거
    images.splice(images.indexOf(imgSrc), 1);

    // 이미지 포함 부모 요소 제거
    this.parentNode.remove();

    // 이미지 카운트 변경
    imageCount.innerText = `${images.length} / 4`;
  });
}

// 상품 등록 취소 버튼
function cancel(productId) {
  const isCanceled = confirm("상품 수정을 취소하시게습니까?");

  if (!isCanceled) {
    return;
  }

  location.href = `/admin/product`;
}

// 상품 삭제 요청 버튼
function deleteReq(productId) {
  const isDeleteRequested = confirm("상품을 삭제 하시겠습니까?");

  if (!isDeleteRequested) {
    return;
  }

  document.getElementById("modal").classList.remove("hidden");

  fetch(`/admin/product?productId=${productId}`, {
    method: "DELETE"
  })
  .then((res) => {
    return res.json();
  })
  .then((data) => {
    alert("삭제 되었습니다.");
    location.href = "/admin/product";
  })
  .catch((err) => {
    alert("서버가 원할하지 않습니다. 잠시후에 이용해주세요.");
    document.getElementById("modal").classList.add("hidden");
  });
}

function handleImageUpload() {
  if (images.length === 4) {
    alert('최대 4장까지 첨부할 수 있습니다.');
    return;
  }

  document.querySelector('#image-input').click();
}

// 첨부된 이미지 미리보기
function previewImage() {
  const preview = document.getElementById('preview');
  const files = document.getElementById('image-input').files;

  if (files.length + images.length > 4) {
    alert('최대 4장까지 첨부할 수 있습니다.');
    return;
  }

  for (let i = 0; i < files.length; i++) {
    if (files[i].size / (1024 * 1024) > 10) {
      alert("한 이미지 용량이 10MB를 초과할 수 없습니다.");
      return;
    }
  }

  for (let i = 0; i < files.length; i++) {
    const file = files[i];

    const container = document.createElement('div');
    container.style.position = 'relative';

    const img = document.createElement('img');
    img.classList.add('preview-image');
    img.style.display = 'flex';
    img.style.width = '300px';
    img.style.height = '300px';
    img.style.border = '1px solid black';
    container.appendChild(img);
    preview.appendChild(container);

    // 이미지 삭제 버튼 태그
    const imgDeleteBtn = document.createElement('button');
    imgDeleteBtn.classList.add('img-delete');
    imgDeleteBtn.style.position = 'absolute';
    imgDeleteBtn.style.right = '1px';
    imgDeleteBtn.style.top = '1px';
    imgDeleteBtn.style.width = '50px';
    imgDeleteBtn.style.height = '50px';
    imgDeleteBtn.style.color = 'red';
    imgDeleteBtn.style.fontWeight = 'bold';
    imgDeleteBtn.style.backgroundColor = 'white';
    imgDeleteBtn.innerText = 'X';

    // 이미지 삭제 이벤트
    imgDeleteBtn.addEventListener('click', function () {
      // 등록된 이미지 src 속성 값
      const imgSrc = this.parentNode.getElementsByTagName('img')[0].src;

      // 이미지 목록 리스트에서 제거
      images.splice(images.indexOf(imgSrc), 1);

      // 이미지 포함 부모 요소 제거
      this.parentNode.remove();

      // 이미지 카운트 변경
      imageCount.innerText = `${images.length} / 4`;
    });

    container.appendChild(imgDeleteBtn);

    const reader = new FileReader();
    reader.onload = function (e) {
      img.src = e.target.result;
      images.push(files[i]);
      imageCount.innerText = `${images.length} / 4`;
    };
    reader.readAsDataURL(file);
  }
}

function updateReq(productId) {
  const isAccepted = confirm("수정 하시겠습니까?");

  if (!isAccepted) {
    alert("수정이 취소되었습니다.");
    return;
  }

  const formData = new FormData();

  images.forEach((e) => {
    formData.append('images', e);
  });

  formData.append("part", part.value);
  formData.append("brand", brand.value);
  formData.append("name", name.value);
  formData.append("stock", stock.value);
  formData.append("price", price.value);

  const description = {};

  for (let i = 0; i < descInputs.length; i++) {
    // if (descInputs[i].classList.contains(convertToEngName(part.value))) {
    const keyNode = descInputs[i].childNodes[1];
    const valueNode = descInputs[i].childNodes[3];

    if (valueNode.value === null || valueNode.value === undefined
        || valueNode.value
        === "") {
      alert("모든 항목을 입력해주세요.");
      return;
    }

    description[keyNode.children[0] ? keyNode.children[0].value
        : keyNode.innerText] = valueNode.value;
  }

  formData.append("description", JSON.stringify(description));

  document.getElementById("modal").classList.remove("hidden");

  fetch(`/admin/product?productId=${productId}`, {
    method: "PUT",
    body: formData,
  })
  .then((res) => {
    return res.json();
  })
  .then((data) => {
    alert("수정되었습니다.");
    location.href = `/user/product/${data.id}`
  })
  .catch(() => {
    alert("서버 상태가 원할하지 않습니다. 잠시 후 이용해주세요.");
    document.getElementById("modal").classList.add("hidden");
  });
}