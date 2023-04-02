package flowerforce.controller.utilities;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * OptionalTypeAdapter to serialize an Optional variable.
 * Code get from https://stackoverflow.com/questions/12161366/how-to-serialize-optionalt-classes-with-gson.
 * @param <E> The type to serialize
 */
public final class OptionalTypeAdapter<E> extends TypeAdapter<Optional<E>> {

    /**
     * Get the type adapter factory.
     */
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            final Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != Optional.class) {
                return null;
            }
            final ParameterizedType parameterizedType = (ParameterizedType) type.getType();
            final Type actualType = parameterizedType.getActualTypeArguments()[0];
            final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(actualType));
            return new OptionalTypeAdapter(adapter);
        }
    };
    private final TypeAdapter<E> adapter;

    /**
     * Get an instance of OptionalTypeAdapter.
     * @param adapter the type adapter to use
     */
    public OptionalTypeAdapter(final TypeAdapter<E> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void write(final JsonWriter out, final Optional<E> value) throws IOException {
        if (value.isPresent()) {
            adapter.write(out, value.get());
        } else {
            out.nullValue();
        }
    }

    @Override
    public Optional<E> read(final JsonReader in) throws IOException {
        final JsonToken peek = in.peek();
        if (peek != JsonToken.NULL) {
            return Optional.ofNullable(adapter.read(in));
        }

        in.nextNull();
        return Optional.empty();
    }
}
