<#import "macros.ftl" as m>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Fact fact fact fact fact</title>
        <@m.scriptsAndCssAndStuff />
    </head>
    <body>
        <h2>Fact a fact!</h2>

        <p>
            ${fact.id}<br>
            ${fact.author}<br>
            ${fact.title}<br>
            ${fact.funFact}<br>
            ${fact.voteCount}<br>
        </p>

        <@m.footer />
    </body>
</html>