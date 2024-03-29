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

package org.taktik.icure.services.external.rest.v2.dto.be.efact

/**
 * Created with IntelliJ IDEA.
 * User: aduchate
 * Date: 19/08/15
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
enum class InvoicingTimeOfDay(val code: Int) {
    Other(0),
    Night(1),
    Weekend(2),
    Bankholiday(3),
    Urgent(4),
    ;

    companion object {

        fun withCode(code: Int): InvoicingTimeOfDay? {
            for (itd in InvoicingTimeOfDay.entries) {
                if (itd.code == code) {
                    return itd
                }
            }
            return null
        }
    }
}
