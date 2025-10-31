# Caching Proxy

A high-performance, lightweight, and scalable caching proxy server built with Spring Boot. This application is designed to sit between your clients and your origin server, caching responses to reduce latency and offload your origin server.

## Table of Contents

* [Features](#features)
* [How It Works](#how-it-works)
* [Getting Started](#getting-started)
* [API Endpoints](#api-endpoints)
* [Usage](#usage)
* [Technologies Used](#technologies-used)
* [Future Enhancements](#future-enhancements)
* [Deployment](#deployment)
* [Testing](#testing)
* [Contributing](#contributing)
* [License](#license)

## Features

*   **Blazing Fast:** Built on Spring Boot and using an in-memory cache, this proxy delivers responses at lightning speed.
*   **Scalable:** Designed to handle a high volume of requests with a minimal memory footprint.
*   **Flexible Configuration:** Easily configure the origin server and manage the cache through a simple REST API.
*   **Transparent Caching:** The `X-Cache` header provides clear insights into whether a response was served from the cache (HIT) or the origin server (MISS).
*   **Lightweight:** Minimal dependencies and a small footprint make it easy to deploy and manage.

## How It Works

1.  A client sends a GET request to the caching proxy.
2.  The proxy checks if the requested resource is in its cache.
3.  **Cache HIT:** If the resource is in the cache, the proxy immediately returns the cached response, adding an `X-Cache: HIT` header.
4.  **Cache MISS:** If the resource is not in the cache, the proxy forwards the request to the origin server. It then caches the response from the origin and returns it to the client with an `X-Cache: MISS` header.

## Getting Started

### Prerequisites

*   Java 21 or later
*   Maven

### Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/your-username/caching-proxy.git
    ```
2.  Navigate to the project directory:
    ```bash
    cd caching-proxy
    ```
3.  Build the project using Maven:
    ```bash
    ./mvnw clean install
    ```
4.  Run the application:
    ```bash
    java -jar target/caching-proxy-0.0.1-SNAPSHOT.jar
    ```

## API Endpoints

### Proxy

*   `GET /proxy/**`: Proxies GET requests to the configured origin server.

### Cache Management

*   `GET /cache`: Get the entire cache map.
*   `DELETE /cache`: Clear the entire cache.
*   `GET /cache/size`: Get the number of items in the cache.

### Configuration

*   `GET /cache/origin`: Get the current origin server URL.
*   `POST /cache/origin`: Set the origin server URL.
    *   **Body:** `{"origin": "https://your-origin-server.com"}`

## Usage

1.  **Set the origin server:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"origin": "https://jsonplaceholder.typicode.com"}' http://localhost:8080/cache/origin
    ```

2.  **Make a request to the proxy:**
    ```bash
    curl -i http://localhost:8080/proxy/todos/1
    ```

    The first time you make this request, you will see `X-Cache: MISS` in the response headers. Subsequent requests for the same URL will result in an `X-Cache: HIT`.

## Technologies Used

*   **Spring Boot:** For building robust and scalable REST APIs.
*   **Maven:** For project build and dependency management.
*   **ConcurrentHashMap:** For a thread-safe, high-performance in-memory cache.

## Future Enhancements

*   **Cache Eviction Policies:** Implement LRU (Least Recently Used) and LFU (Least Frequently Used) cache eviction policies.
*   **Time-to-Live (TTL):** Add support for setting a TTL for cached items.
*   **Distributed Cache:** Integrate with a distributed cache like Redis or Hazelcast for multi-node deployments.
*   **Metrics and Monitoring:** Expose metrics for cache performance and integrate with monitoring tools like Prometheus and Grafana.
*   **Web UI:** A simple web UI to manage the cache and configuration.

## Deployment

This application can be deployed as a standalone JAR file. You can also containerize it using Docker for easier deployment and scaling.

### Docker

1.  Create a `Dockerfile` in the root of the project:

    ```Dockerfile
    FROM openjdk:21-jdk-slim
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```

2.  Build the Docker image:

    ```bash
    docker build -t caching-proxy .
    ```

3.  Run the Docker container:

    ```bash
    docker run -p 8080:8080 caching-proxy
    ```

## Testing

This project uses JUnit 5 for unit testing. To run the tests, execute the following command:

```bash
./mvnw test
```

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue.

## License

This project is licensed under the MIT License.