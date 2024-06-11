const imageContainer = document.getElementById("image-container");
const prevButton = document.getElementById("prevButton");
const nextButton = document.getElementById("nextButton");
const imagePage = document.getElementById("imagePage");
const images = document.getElementsByClassName("images");

let currentPage = 0; // 이미지 슬라이드 현재 페이지(인덱스)
const imageCount = document.getElementsByClassName("images").length; // 이미지 갯수

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