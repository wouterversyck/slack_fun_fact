<#import "macros.ftl" as m>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Fact fact fact fact fact</title>
        <@m.scriptsAndCssAndStuff />
    </head>
    <body>
    <h2>Add ad fact</h2>

        <form action="/view/funfact" method="post">
            Author:<br>
            <input type="text" name="author">
            <br><br>
            Title:<br>
            <input type="text" name="title">
            <br><br>
            Fact:<br>
            <input type="text" name="funFact">
            <br><br>
            <input type="submit" value="Submit">
        </form>

        <@m.footer />
    </body>
</html>