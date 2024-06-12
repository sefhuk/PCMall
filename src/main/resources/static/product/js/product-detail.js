const imageContainer = document.getElementById("image-container");
const prevButton = document.getElementById("prevButton");
const nextButton = document.getElementById("nextButton");
const imagePage = document.getElementById("imagePage");
const images = document.getElementsByClassName("images");
const buyCount = document.getElementById("buyCount");

let currentPage = 0; // 이미지 슬라이드 현재 페이지(인덱스)
const imageCount = document.getElementsByClassName("images").length; // 이미지 갯수

if (prevButton && prevButton && nextButton) {
  prevButton.addEventListener("click", function (event) {
    event.preventDefault();

    if (currentPage === 0) {
      return;
    }

    imageContainer.style.transform = `translateX(-${--currentPage * 400}px)`;
    imagePage.innerHTML = `${currentPage + 1}/${imageCount}`;
  });

  nextButton.addEventListener("click", function (event) {
    event.preventDefault();

    if (currentPage + 1 === imageCount) {
      return;
    }

    imageContainer.style.transform = `translateX(-${++currentPage * 400}px)`;
    imagePage.innerHTML = `${currentPage + 1}/${imageCount}`;
  });
}

// 구매 수량 입력 핸들링
buyCount.addEventListener("input", function (event) {
  const count = parseInt(event.target.value);
  const min = parseInt(event.target.min);
  const max = parseInt(event.target.max);

  if (count > max) {
    alert("구매 수량은 재고 수량보다 많을 수 없습니다.");
    buyCount.value = max;
    return;
  }

  if (count < 0) {
    alert("1개 이상만 구매가 가능합니다.");
    buyCount.value = min;
  }
});