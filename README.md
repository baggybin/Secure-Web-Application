# Secure-Web-Application
Secure Web Application
Your task is to write an online shop application using EJB, entity classes and servlets/JSP/HTML (th
amazon or something similar). Customers browse through your offerings, add or remove them fro
their shopping cart and eventually either check out their order or cancel it. Your store can s
whatever you like: books, computers, cars, etc. However, you have to provide the following featur
x Access to your shop is limited – you must provide an authentication scheme. Access rights a
role based, where your system provides two roles: customer and administrator.
x Provide (at least) two accounts: Customer joe with password “1tbh?5g” and administrator to
with password “4uIdo0!” (feel free to add other accounts, but these must exist).
x Customers can perform the following:
o Browse through all your items.
o Search products by ID number and browse through the search results.
o Search products by name and browse through the search results. 
o Add displayed items to their shopping cart.
o Remove items from their shopping cart.
o Add comments to any product.
o View comments that have been added to a product.
x Administrators can perform:
o Add new products to the sale database.
o Remove products from the sale database.
o Increase/decrease the available amount of any product.
x When customers check out, the quantity for your items in the database is adjust
correspondingly - make sure the quantity of a product in the database cannot drop below 0.
x When customers cancel their order, the database should remain unchanged.
x A logging facility:
o Every time a customer confirms an order or cancels an order a corresponding entry
added to the log (use either a log-file or a table in the database).
o Every time an administrator adds or removes a product a corresponding entry is add
to the log.
x Your application must avoid the following OWASP Top 10 vulnerabilities:
o A1: Injection
o A2: Broken Authentication & Session Management
o A3: Cross-Site Scripting (XSS)
o A4: Insecure Direct Object References
o A7: Missing Function Level Access Control
o A8: Cross Site Request Forgery (CSRF)
o A10: Unvalidated Redirects and Forwards (your application must contain at least o
user input-dependant redirect or forward)
x You must include a document in your submission that discusses:
o What techniques you used to ensure that your application it not vulnerable to t
required OWASP Top 10 vulnerabilities.
o How you tested your application to ensure your chosen defence is working correctly.
