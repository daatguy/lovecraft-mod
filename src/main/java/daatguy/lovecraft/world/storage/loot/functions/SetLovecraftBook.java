package daatguy.lovecraft.world.storage.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;

import daatguy.lovecraft.item.SubItemsHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetNBT;

public class SetLovecraftBook extends LootFunction
{
    private final String book;

    public SetLovecraftBook(LootCondition[] conditionsIn, String book) {
    	super(conditionsIn);
        this.book = book;
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
        NBTTagCompound nbt;
        if(stack.hasTagCompound()) {
        	nbt = stack.getTagCompound();
        } else {
        	nbt = new NBTTagCompound();
        }
        nbt.setString("Book",this.book);
        stack.setTagCompound(nbt);
        stack.setItemDamage(SubItemsHandler.books.get(this.book).color);
        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<SetLovecraftBook>
        {
            public Serializer()
            {
                super(new ResourceLocation("set_lovecraft_book"), SetLovecraftBook.class);
            }

            public void serialize(JsonObject object, SetLovecraftBook functionClazz, JsonSerializationContext serializationContext)
            {
                object.addProperty("book", functionClazz.book);
            }

            public SetLovecraftBook deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
            {
            	System.out.println(object.toString());
            	String book = JsonUtils.getString(object, "book");
                return new SetLovecraftBook(conditionsIn, book);
            }
        }
}