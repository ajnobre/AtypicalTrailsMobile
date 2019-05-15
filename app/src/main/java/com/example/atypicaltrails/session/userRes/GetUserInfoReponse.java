package com.example.atypicaltrails.session.userRes;

public class GetUserInfoReponse {
    private Key key;
    private PropertyMap propertyMap;

    public GetUserInfoReponse(Key key, PropertyMap propertyMap) {
        this.key = key;
        this.propertyMap = propertyMap;
    }

    public Key getKey() {
        return key;
    }

    public PropertyMap getPropertyMap() {
        return propertyMap;
    }
}
