const API_BASE = 'http://localhost:8080/api';

function showMsg(el, text, isError) {
  el.textContent = text;
  el.className = 'msg ' + (isError ? 'error' : 'success');
}

function requireLogin() {
  if (!sessionStorage.getItem('loggedInUser')) {
    window.location.href = 'login.html';
  }
}

function logout() {
  sessionStorage.removeItem('loggedInUser');
  window.location.href = 'login.html';
}
