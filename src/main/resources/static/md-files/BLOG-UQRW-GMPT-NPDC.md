<div class="message-card success" th:if="${message}" th:text="${message}">
  <button class="close-actions">
    <i class="fa-solid fa-times"></i>
  </button>
</div>
<div class="message-card error" th:if="${error}" th:text="${error}">
  <button class="close-actions">
    <i class="fa-solid fa-times"></i>
  </button>
</div>