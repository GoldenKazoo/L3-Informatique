function isValidDate(value) {
  const regex = /^\d{4}-\d{2}-\d{2}$/;
  if (!regex.test(value)) return false;
  const [year, month, day] = value.split("-").map(Number);
  const date = new Date(year, month - 1, day);
  return date.getFullYear() === year &&
         date.getMonth() === month - 1 &&
         date.getDate() === day;
}

function isValidTimestamp(value) {
  const regex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
  if (!regex.test(value)) return false;
  const [datePart, timePart] = value.split(" ");
  const [year, month, day] = datePart.split("-").map(Number);
  const [hour, minute, second] = timePart.split(":").map(Number);
  const date = new Date(Date.UTC(year, month - 1, day, hour, minute, second));
  console.log(
    date.getUTCFullYear(), year,
    date.getUTCMonth(), month - 1,
    date.getUTCDate(), day,
    date.getUTCHours(), hour,
    date.getUTCMinutes(), minute,
    date.getUTCSeconds(), second
  );
  return date.getUTCFullYear() === year &&
    date.getUTCMonth() === month - 1 &&
    date.getUTCDate() === day &&
    date.getUTCHours() === hour &&
    date.getUTCMinutes() === minute &&
    date.getUTCSeconds() === second;
}

function isValidPhone(value) {
  const regex = /^(\+33|0)[1-9](\d{2}){4}$/;
  return regex.test(value.replace(/\s+/g, ''));
}

function isValidSex(value) {
  return ['M','F'].includes(value);
}

function isValidNIR(value) {
  return /^[12][0-9]{2}(0[1-9]|1[0-2])([0-9][0-9]|2A|2B)[0-9]{8}$/.test(value);
}

function isValidADELI(value) {
  return /^[0-9]{9}$/.test(value);
}

function isValidEmail(value) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
}

function isNotEmptyText(value) {
  return typeof value === 'string' && value.trim().length > 0;
}

function isValidFee(value) {
  const n = Number(value);
  console.log(n);
  return (
    !isNaN(n) &&
    isFinite(n) && 
    (n >= 0) && 
    /^\d+(\.\d{1,2})?$/.test(n.toString()));
}

module.exports = {
    isValidDate,
    isValidTimestamp,
    isValidPhone,
    isValidSex,
    isValidNIR,
    isValidADELI,
    isValidEmail,
    isValidFee,
    isNotEmptyText,
};
