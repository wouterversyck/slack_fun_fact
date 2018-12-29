<#import "macros.ftl" as m>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome</title>
        <@m.scriptsAndCssAndStuff />
    </head>
    <body>
        <h1>Hello ${example}</h1>
        <a href="/view/funfact">Add fun fact</a>
        
        <@m.footer />
    </body>
</html>