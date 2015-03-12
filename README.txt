Team: RC_Wonders

Questions:

1.  (4) -> (5)
    (5) -> (6)

2. We ensure that the item was purchased exactly at the Buy_Price of that particular item by storing the Buy_Price of the item in an the HttpSession of the request before displaying the item information. When the user opts to buy the item, we do not retrieve the item price from the client side (ie a request param), and instead retrieve it from the session.

Helpful Resources:

1)  https://cloud.google.com/appengine/docs/java/config/webxml
    We were having trouble figuring out how to transition the flow from HTTP to HTTPS in a Java servlet. We couldn't figure out what was going wrong, but we just needed to add the information in the "Secure URLs" portion of the above link.