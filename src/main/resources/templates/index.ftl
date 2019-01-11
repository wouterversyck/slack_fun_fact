<#import "macros.ftl" as m>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome</title>
        <@m.scriptsAndCssAndStuff />
    </head>
    <body>
        <h1>Hello ${principal.getName()}, authenticated: ${principal.isAuthenticated()?string('yes','no, you should not be here')}</h1>
        <a href="/view/funfact">Add fun fact</a>

        <@m.footer />
    </body>
</html>