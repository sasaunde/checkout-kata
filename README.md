# checkout-kata
Some example code to create a checkout system using services - expect IoC framework to wire them.


CheckoutServiceImpl uses Price Service to pull in price data for a given SKU. It uses Offers Service to pull in offers data for a given SKU.

SKU CRUD is not implemented.

Price CRUD is not implemented.

Offers CRUD is not implemented.

BigDecimal used to represent currency.

Simple version of a Scales service is implemented, which allows some leeway when matching scales weight with expected basket contents weight.
