package com.example.akarpenko.basekotlin.domain.repositories.network;


public interface IRestAdapter {

    <T> T createApi(Class<T> clazz, String base_url);

}
