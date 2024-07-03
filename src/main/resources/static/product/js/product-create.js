const imageCount = document.getElementById('image-count');
const images = []; // 미리보기 이미지 리스트

// 상품 설명 폼 데이터
const part = document.getElementById("part");
const brand = document.getElementById("brand");
const name = document.getElementById("name");
const stock = document.getElementById("stock");
const price = document.getElementById("price");

function handleImageUpload() {
  if (images.length === 4) {
    alert('최대 4장까지 첨부할 수 있습니다.');
    return;
  }

  document.querySelector('#image-input').click();
}

// 첨부된 이미지 미리보기
function previewImages() {
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

// 상품 등록
function upload() {
  const isAccepted =  confirm("등록하시겠습니까?");

  if (!isAccepted) {
    alert("등록이 취소되었습니다.");
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

  for (let i = 5; i < descInputs.length; i++) {
    if (descInputs[i].classList.contains(convertToEngName(part.value))) {
      const keyNode = descInputs[i].childNodes[1];
      const valueNode = descInputs[i].childNodes[3];

      if (valueNode.value === null || valueNode.value === undefined || valueNode.value
          === "") {
        alert("모든 항목을 입력해주세요.");
        return;
      }

      description[keyNode.children[0] ? keyNode.children[0].value
          : keyNode.innerText] = valueNode.value;
    }
  }

  formData.append("description", JSON.stringify(description));

  document.getElementById("modal").classList.remove("hidden");

  fetch('/admin/product', {
    method: 'POST',
    body: formData,
  })
  .then((res) => {
    if (res.status === 200 || res.status === 201) {
      alert("등록 완료");
      return res.json();
    } else {
      alert("요청 오류");
      document.getElementById("modal").classList.add("hidden");
    }
  })
  .then((data) => {
    location.href=`/user/product/${data.id}`
  })
  .catch(() => {
    alert("문제 발생");
    document.getElementById("modal").classList.add("hidden");
  });
}

const descInputs = document.getElementById("inputForm").getElementsByTagName(
    "div");
const brandOptions = document.getElementsByClassName("brandOption");

// 부품 선택에 따른 입력 폼 변화 주기
function handleChangePart() {
  const partName = part.value;

  // description 변화
  for (let i = 5; i < descInputs.length; i++) {
    if (!descInputs[i].classList.contains(convertToEngName(partName))) {
      descInputs[i].classList.add("hidden");
      continue;
    }

    descInputs[i].classList.remove("hidden");
  }

  // 제조사 변화
  for (let i = 0; i < brandOptions.length; i++) {
    if (!brandOptions[i].classList.contains(convertToEngName(partName))) {
      brandOptions[i].classList.add("hidden");
      brandOptions[i].selected = false;
      continue;
    }

    brandOptions[i].classList.remove("hidden");
    brandOptions[i].selected = true;
  }
}

// 상품 등록 취소 버튼
function cancel() {
  const isCanceled = confirm("상품 등록을 취소하시겠습니까?");

  if (!isCanceled) {
    return;
  }

  location.href = "/user/product";
}

// 브랜드 option 목록 초기화
for (let i = 0; i < brandOptions.length; i++) {
  if (brandOptions[i].classList.contains("cpu")) {
    brandOptions[i].classList.remove("hidden");
  }
}