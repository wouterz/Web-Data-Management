if [$1 == ""]; then
    echo "hello"
fi
gradle :eureka-server:build
gradle :zuul-server:build
gradle :order-server:build
gradle :user-server:build
gradle :payment-server:build
gradle :stock-server:build