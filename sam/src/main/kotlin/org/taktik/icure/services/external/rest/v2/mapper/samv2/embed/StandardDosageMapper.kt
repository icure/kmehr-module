/*
 *  iCure Data Stack. Copyright (c) 2020 Taktik SA
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful, but
 *     WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public
 *     License along with this program.  If not, see
 *     <https://www.gnu.org/licenses/>.
 */

package org.taktik.icure.services.external.rest.v2.mapper.samv2.embed

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.taktik.icure.entities.samv2.embed.StandardDosage
import org.taktik.icure.services.external.rest.v2.dto.samv2.embed.StandardDosageDto

@Mapper(componentModel = "spring", uses = [QuantityV2Mapper::class, IndicationV2Mapper::class, BoundedParameterV2Mapper::class, RouteOfAdministrationV2Mapper::class, SamTextV2Mapper::class], injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface StandardDosageV2Mapper {
	fun map(standardDosageDto: StandardDosageDto): StandardDosage
	fun map(standardDosage: StandardDosage): StandardDosageDto
}
