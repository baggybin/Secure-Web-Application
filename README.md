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
Browse through all your items.
Search products by ID number and browse through the search results.
Search products by name and browse through the search results. 
Add displayed items to their shopping cart.
Remove items from their shopping cart.
Add comments to any product.
View comments that have been added to a product.

x Administrators can perform:
Add new products to the sale database.
Remove products from the sale database.
Increase/decrease the available amount of any product.

x When customers check out, the quantity for your items in the database is adjust
correspondingly - make sure the quantity of a product in the database cannot drop below 0.
x When customers cancel their order, the database should remain unchanged.

Every time a customer confirms an order or cancels an order a corresponding entry
added to the log (use either a log-file or a table in the database).
Every time an administrator adds or removes a product a corresponding entry is add
to the log.

x Your application must avoid the following OWASP Top 10 vulnerabilities:

