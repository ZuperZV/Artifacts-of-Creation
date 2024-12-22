package net.zuperzv.artifacts_of_creation.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ArtifactsOfCreation.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ArtifactsOfCreation.MOD_ID);

    public static void register(IEventBus eventBus){
        RECIPE_TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }

    public static final Supplier<RecipeType<RightClickRecipe>> RIGHT_CLICK_RECIPE_TYPE =
            RECIPE_TYPES.register("right_click", () -> RightClickRecipe.Type.INSTANCE);



    public static final Supplier<RecipeSerializer<RightClickRecipe>> RIGHT_CLICK_SERIALIZER =
            SERIALIZERS.register("right_click", () -> RightClickRecipe.Serializer.INSTANCE);


}