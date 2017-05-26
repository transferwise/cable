# Cable [![CircleCI](https://circleci.com/gh/transferwise/cable/tree/master.svg?style=svg)](https://circleci.com/gh/transferwise/cable/tree/master)

A simple and minimalistic url rewrite module.

## Installation

Just add the following configuration to your `build.gradle` file

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.transferwise:cable:1.0.0'
}
```

## Configuration

Integration with Spring.

```java
@Bean
public Filter urlRewriteFilter() {
    return new UrlRewriteFilter()
        .rewrite("/path", "/more/interesting/path")
        .rewrite("^/([a-z]{2})/(.*)$", "/$1/blog/$2");
}
```

## Usage

In Thymeleaf templates it will work automatically with link url expressions `@{...}`

```html
<a th:href="@{/test}">This is a test</a>
```

It will also automatically work on redirects within your controllers.

## Advanced use

You can use lambda functions tu create more complex filters, so you will rewrite the url under controlled circumstances.

Let's imagine you want to rewrite only the urls of requests served under "/path":


```java 
@Bean
public Filter urlRewriteFilter() {
    return new UrlRewriteFilter()
        .rewrite("/test", "/test2", r -> r.getRequestURI().contains("/path"));
}
``` 