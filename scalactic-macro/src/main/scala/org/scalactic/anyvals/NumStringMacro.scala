/*
* Copyright 2001-2014 Artima, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.scalactic.anyvals

import reflect.macros.Context
import org.scalactic.Resources

import CompileTimeAssertions._
private[scalactic] object NumStringMacro {

  def isValid(s: String): Boolean = s.forall(c => c >= '0' && c <= '9')

  def apply(c: Context)(value: c.Expr[String]): c.Expr[NumString] = {
    val notValidMsg =
      "NumString.apply can only be invoked on String literals that contain numeric characters, i.e., decimal digits '0' through '9', " +
      "like \"123\"."
    val notLiteralMsg =
      "NumString.apply can only be invoked on String literals that contain only numeric characters, i.e., decimal digits '0' through '9', like \"123\"" +
      " Please use NumString.from instead."
    ensureValidStringLiteral(c)(value, notValidMsg, notLiteralMsg)(isValid)
    c.universe.reify { NumString.ensuringValid(value.splice) }
  } 
}
