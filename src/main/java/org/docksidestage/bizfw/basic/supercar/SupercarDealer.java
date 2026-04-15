/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.supercar;

import org.docksidestage.bizfw.basic.supercar.SupercarManufacturer.Supercar;
import org.docksidestage.bizfw.basic.supercar.exception.InsufficientPartsException;
import org.docksidestage.bizfw.basic.supercar.exception.SuperCarManufacturingException;

/**
 * The dealer(販売業者) of supercar.
 * @author jflute
 */
public class SupercarDealer {

    public Supercar orderSupercar(String clientRequirement) {
        SupercarManufacturer supercarManufacturer = createSupercarManufacturer();
        if (clientRequirement.contains("steering wheel is like sea")) {
            // TODO jflute 他のif文の中でも例外が発生する可能性は否めないと思うのですが、このブロックにだけtry-catchを入れるのは妥当でしょうか...? by akinari.tsuji (2026/03/18)
            // [へんじ] まあ妥当ではないですね。全体で囲っておきたいcatchです。 (2026/03/18)
            Supercar supercar;
            try {
                // TODO tsuji InsufficientPartsException を catch してない by jflute (2026/03/18)
                // 修正しました！
                 supercar = supercarManufacturer.makeSupercar("piari");
            } catch (InsufficientPartsException e) {
                throw new SuperCarManufacturingException("スーパーカーの製造に失敗しました: clientRequirement=" + clientRequirement, e);
            }
            return supercar;
        } else if (clientRequirement.contains("steering wheel is useful on land")) {
            return supercarManufacturer.makeSupercar("land");
        } else if (clientRequirement.contains("steering wheel has many shop")) {
            return supercarManufacturer.makeSupercar("piari");
        } else {
            throw new IllegalStateException("Cannot understand the client requirement: " + clientRequirement);
        }
    }

    protected SupercarManufacturer createSupercarManufacturer() {
        return new SupercarManufacturer();
    }
}
