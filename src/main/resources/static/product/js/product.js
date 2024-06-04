let imageCount = document.getElementById('image-count');
let images = []; // 미리보기 이미지 리스트

// 상품 설명 폼 데이터
let category = document.getElementById("category");
let brand = document.getElementById("brand");
let name = document.getElementById("name");
let stock = document.getElementById("stock");
let price = document.getElementById("price");
let coreCount = document.getElementById("core_count");
let threadCount = document.getElementById("thread_count");
let baseClockSpeed = document.getElementById("base_clock_speed");
let boostClockSpeed = document.getElementById("boost_clock_speed");

// 숫자 입력 형태 포맷
function formatNumber(input) {
  let value = input.value.replace(/\D/g, ''); // 숫자 이외의 문자 제거
  value = value.replace(/^0+/, ''); // 맨 앞의 연속된 0 제거
  input.value = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','); // 콤마로 구분된 문자열로 변경하여 입력값에 설정
}

function handleImageUpload() {
  if (images.length === 4) {
    alert('최대 4장까지 첨부할 수 있습니다.');
    return;
  }

  document.querySelector('#image-input').click();
}

// 첨부된 이미지 미리보기
function previewImages() {
  let preview = document.getElementById('preview');
  let files = document.getElementById('image-input').files;

  if (files.length + images.length > 4) {
    alert('최대 4장까지 첨부할 수 있습니다.');
    return;
  }

  for (let i = 0; i < files.length; i++) {
    let file = files[i];

    let container = document.createElement('div');
    container.style.position = 'relative';

    let img = document.createElement('img');
    img.classList.add('preview-image');
    img.style.display = 'flex';
    img.style.width = '300px';
    img.style.height = '300px';
    img.style.border = '1px solid black';
    container.appendChild(img);
    preview.appendChild(container);

    // 이미지 삭제 버튼 태그
    let imgDeleteBtn = document.createElement('button');
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
      let imgSrc = this.parentNode.getElementsByTagName('img')[0].src;

      // 이미지 목록 리스트에서 제거
      images.splice(images.indexOf(imgSrc), 1);

      // 이미지 포함 부모 요소 제거
      this.parentNode.remove();

      // 이미지 카운트 변경
      imageCount.innerText = `${images.length} / 4`;
    });

    container.appendChild(imgDeleteBtn);

    let reader = new FileReader();
    reader.onload = function (e) {
      img.src = e.target.result;
      images.push(files[i]);
      imageCount.innerText = `${images.length} / 4`;
    };
    reader.readAsDataURL(file);
  }
}

// 서버로 이미지 전송
function uploadImage() {
  let formData = new FormData();

  images.forEach((e) => {
    formData.append('images', e);
  });

  const data = {
    categoryId: category.value,
    brand: brand.value,
    name: name.value,
    stock: convertToNumber(stock.value),
    price: convertToNumber(price.value),
    coreCount: convertToNumber(coreCount.value),
    threadCount: convertToNumber(threadCount.value),
    baseClockSpeed: baseClockSpeed.value,
    boostClockSpeed: boostClockSpeed.value,
  }

  const blob = new Blob([JSON.stringify(data)], {type: 'application/json'});
  formData.append("desc", blob);

  fetch('/product', {
    method: 'POST',
    body: formData,
  })
  .then((res) => {
    if (res.status === 200 || res.status === 201) {
      alert("등록 완료");
    } else {
      alert("요청 오류");
    }
  })
  .catch(() => {
    alert("문제 발생");
  });
}
