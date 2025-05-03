package org.geyser.extension.cordslice.Random;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.geyser.extension.cordslice.Events.EventManager;
import org.geyser.extension.cordslice.Events.JavaPacketEvent;
import org.geyser.extension.cordslice.CordSliceExtension;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.packet.Packet;

public class PacketHandlers {
    /*public static BedrockPacketHandler createBedrockHandler(@NonNull GeyserConnection connection, BedrockPacketHandler packetHandler) {
        return new BedrockPacketHandler() {
            @Override
            public PacketSignal handlePacket(BedrockPacket packet) {
                BedrockPacketEvent packetEvent = new BedrockPacketEvent(packet, connection.connection());
                EventManager.call(packetEvent);

                if (packetEvent.isCanceled()) return PacketSignal.HANDLED;

                return packetHandler.handlePacket(packet);
            }

            @Override
            public void onDisconnect(String reason) {
                packetHandler.onDisconnect(reason);
            }

            @Override
            public PacketSignal handle(AdventureSettingsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AnimatePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AnvilDamagePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AvailableEntityIdentifiersPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(BlockEntityDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(BlockPickRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(BookEditPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientCacheBlobStatusPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientCacheMissResponsePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientCacheStatusPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientToServerHandshakePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CommandBlockUpdatePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CommandRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CompletedUsingItemPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ContainerClosePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CraftingEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EducationSettingsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EmotePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EntityEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EntityFallPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EntityPickRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(FilterTextPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(InteractPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(InventoryContentPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(InventorySlotPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(InventoryTransactionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ItemFrameDropItemPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LabTablePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LecternUpdatePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LevelEventGenericPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LevelSoundEvent1Packet packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LevelSoundEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LoginPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MapInfoRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MobArmorEquipmentPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MobEquipmentPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ModalFormResponsePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MoveEntityAbsolutePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MovePlayerPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MultiplayerSettingsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(NetworkStackLatencyPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PhotoTransferPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerActionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerAuthInputPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerHotbarPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerInputPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerSkinPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PurchaseReceiptPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RequestChunkRadiusPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ResourcePackChunkRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ResourcePackClientResponsePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RiderJumpPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ServerSettingsRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetDefaultGameTypePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetLocalPlayerAsInitializedPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetPlayerGameTypePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SubClientLoginPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddBehaviorTreePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddHangingEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddItemEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddPaintingPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddPlayerPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AvailableCommandsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(BlockEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(BossEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ChangeDimensionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ChunkRadiusUpdatedPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientboundMapItemDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CommandOutputPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ContainerOpenPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ContainerSetDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CraftingDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(DisconnectPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ExplodePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LevelChunkPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(GameRulesChangedPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(GuiDataPickItemPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(HurtArmorPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AutomationClientConnectPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LevelEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MapCreateLockedCopyPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MobEffectPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ModalFormRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MoveEntityDeltaPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(NetworkSettingsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(NpcRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(OnScreenTextureAnimationPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerListPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlaySoundPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayStatusPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RemoveEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RemoveObjectivePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ResourcePackChunkDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ResourcePackDataInfoPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ResourcePacksInfoPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ResourcePackStackPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RespawnPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ScriptCustomEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ServerSettingsResponsePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ServerToClientHandshakePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetCommandsEnabledPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetDifficultyPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetDisplayObjectivePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetEntityDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetEntityLinkPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetEntityMotionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetHealthPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetLastHurtByPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetScoreboardIdentityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetScorePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetSpawnPositionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetTimePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SettingsCommandPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetTitlePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ShowCreditsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ShowProfilePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ShowStoreOfferPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SimpleEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SpawnExperienceOrbPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SpawnParticleEffectPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(StartGamePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(StopSoundPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(StructureBlockUpdatePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(StructureTemplateDataRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(StructureTemplateDataResponsePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(TakeItemEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(TextPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(TickSyncPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(TransferPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateAttributesPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateBlockPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateBlockPropertiesPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateBlockSyncedPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateEquipPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateSoftEnumPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateTradePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(BiomeDefinitionListPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LevelSoundEvent2Packet packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(NetworkChunkPublisherUpdatePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(VideoStreamConnectPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CodeBuilderPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EmoteListPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ItemStackRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ItemStackResponsePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerArmorDamagePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerEnchantOptionsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CreativeContentPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdatePlayerGameTypePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PositionTrackingDBServerBroadcastPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PositionTrackingDBClientRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PacketViolationWarningPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(DebugInfoPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MotionPredictionHintsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AnimateEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraShakePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CorrectPlayerMovePredictionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerFogPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ItemComponentPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientboundDebugRendererPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SyncEntityPropertyPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AddVolumeEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RemoveVolumeEntityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(NpcDialoguePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SimulationTypePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EduUriResourcePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CreatePhotoPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateSubChunkBlocksPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SubChunkPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SubChunkRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PhotoInfoRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(PlayerStartItemCooldownPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ScriptMessagePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CodeBuilderSourcePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(TickingAreasLoadStatusPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(DimensionDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AgentActionEventPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ChangeMobPropertyPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(LessonProgressPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RequestAbilityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RequestPermissionsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ToastRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateAbilitiesPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateAdventureSettingsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(DeathInfoPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(EditorNetworkPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(FeatureRegistryPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ServerStatsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RequestNetworkSettingsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(GameTestRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(GameTestResultsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UpdateClientInputLocksPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientCheatAbilityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraPresetsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraInstructionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(UnlockedRecipesPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CompressedBiomeDefinitionListPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(TrimDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(OpenSignPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AgentAnimationPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(RefreshEntitlementsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ToggleCrafterSlotRequestPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetPlayerInventoryOptionsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetHudPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(AwardAchievementPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ClientboundCloseFormPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ServerboundLoadingScreenPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(JigsawStructureDataPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CurrentStructureFeaturePacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ServerboundDiagnosticsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraAimAssistPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(ContainerRegistryCleanupPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MovementEffectPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(SetMovementAuthorityPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraAimAssistPresetsPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(CameraAimAssistInstructionPacket packet) {
                return packetHandler.handle(packet);
            }

            @Override
            public PacketSignal handle(MovementPredictionSyncPacket packet) {
                return packetHandler.handle(packet);
            }
        };
    }*/


    public static ChannelHandler createJavaHandler() {
        return new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof Packet packet) {
                    GeyserSession session = ctx.channel().attr(CordSliceExtension.SESSION_KEY).get();

                    JavaPacketEvent packetEvent = new JavaPacketEvent(packet, session);
                    EventManager.call(packetEvent);

                    if (packetEvent.isCanceled()) return;
                }
                super.channelRead(ctx, msg);
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                super.write(ctx, msg, promise);
            }
        };
    }
}
