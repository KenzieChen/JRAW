package net.dean.jraw.models.meta;

import net.dean.jraw.models.CommentMessage;
import net.dean.jraw.models.Message;
import net.dean.jraw.models.PrivateMessage;
import org.codehaus.jackson.JsonNode;

/**
 * This class is responsible for serializing Message subclasses. It accepts JsonNodes whose "kind" node are equal
 * to {@link Model.Kind#COMMENT} and {@link Model.Kind#MESSAGE}.
 */
public class MessageSerializer implements JsonSerializer<Message> {
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> T parse(JsonNode node, Class<T> clazz) {
        Model.Kind k = Model.Kind.getByValue(node.get("kind").asText());
        switch (k) {
            case COMMENT:
                return (T) new CommentMessage(node.get("data"));
            case MESSAGE:
                return (T) new PrivateMessage(node.get("data"));
            default:
                throw new IllegalArgumentException("Kind " + k.getValue() + " is not applicable for class " + clazz.getName());
        }
    }
}
