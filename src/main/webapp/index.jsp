<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Список книг</title>
</head>
<body>
<h2>Library</h2>
<form th:method="GET" action="/people">
    <input type="submit" value="Readers" />
</form>
<form th:method="GET" action="/books">
    <input type="submit" value="Books" />
</form>
</body>
</html>
