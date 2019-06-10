if [$1 == ""]; then
    echo "hello"
fi
echo "Eureka"
gradle :eureka-server:build
echo "Zuul"
gradle :zuul-server:build
echo "Order"
gradle :order-service:build
echo "User"
gradle :user-service:build
echo "Payment"
gradle :payment-service:build
echo "Stock"
gradle :stock-service:build
