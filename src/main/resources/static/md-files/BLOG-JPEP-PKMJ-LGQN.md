<!-- Check if there is a success message -->
<div th:if="${message}">
    <p th:text="${message}" class="success-message"></p>
</div>

<!-- Check if there is an error message -->
<div th:if="${error}">
    <p th:text="${error}" class="error-message"></p>
</div>