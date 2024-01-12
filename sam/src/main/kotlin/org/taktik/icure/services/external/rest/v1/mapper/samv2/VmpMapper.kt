/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.mapper.samv2

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.taktik.icure.entities.samv2.Vmp
import org.taktik.icure.services.external.rest.v1.dto.samv2.VmpDto
import org.taktik.icure.services.external.rest.v1.mapper.EntityReferenceMapper
import org.taktik.icure.services.external.rest.v1.mapper.samv2.embed.*

@Mapper(componentModel = "spring", uses = [VtmMapper::class, SamTextMapper::class, VmpGroupStubMapper::class, CommentedClassificationMapper::class, VmpComponentMapper::class, EntityReferenceMapper::class, WadaMapper::class], injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface VmpMapper {
	@Mappings(
		Mapping(target = "attachments", ignore = true),
		Mapping(target = "revHistory", ignore = true),
		Mapping(target = "conflicts", ignore = true),
		Mapping(target = "revisionsInfo", ignore = true)
	)
	fun map(vmpDto: VmpDto): Vmp
	fun map(vmp: Vmp): VmpDto
}
