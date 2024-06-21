const searchContent = document.getElementById("search-content");
const searchCategory = document.getElementById("search-category");
const searchButton = document.getElementById("search-button");

searchContent.addEventListener("keydown", (event) => {
  if (event.key === 'Enter') {
    event.preventDefault();
    searchButton.click();
  }
});

searchButton.addEventListener("click", () => {
  location.href = `/user/product?category=${category}&searchType=${searchCategory.value}&search=${searchContent.value}`;
});
