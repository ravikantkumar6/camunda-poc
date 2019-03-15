var dummyPayload = S(payload);
var dummyPayloadJson = JSON.parse(dummyPayload);
print("***************"+dummyPayload );
var value = S(NUMBER, 'application/json');
var json = JSON.parse(value);
dummyPayloadJson.id =  json.id;
print("^^^^^^^^^^^^"+ JSON.stringify(dummyPayloadJson));
JSON.stringify(dummyPayloadJson);