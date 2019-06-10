if [$1 == ""]; then
    echo "hello"
fi
gradle :eureka-server:build
gradle :zuul-server:build
gradle :order-service:build
gradle :user-service:build
gradle :payment-service:build
gradle :stock-service:build
