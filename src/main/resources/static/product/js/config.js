function convertToEngName(parValue) {
  switch (parValue) {
    case "CPU":
      return "cpu";
    case "쿨러/튜닝":
      return "cooler"
    case "메인보드":
      return "mainboard"
    case "메모리":
      return "ram"
    case "그래픽카드":
      return "gpu"
    case "SSD":
      return "ssd";
    case "HDD":
      return "hdd";
    case "케이스":
      return "case";
  }
}