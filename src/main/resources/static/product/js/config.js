// 문자형 숫자를 숫자로 변환
function convertToNumber(commaString) {
  const numberString = commaString.replace(/,/g, '');
  return parseFloat(numberString);
}