package com.ntvinhdev.kodeco.jetreddit.data.database.dbmapper

import com.ntvinhdev.kodeco.jetreddit.data.database.model.PostDbModel
import com.ntvinhdev.kodeco.jetreddit.domain.model.PostModel

interface DbMapper {

  fun mapPost(dbPostDbModel: PostDbModel): PostModel

  fun mapDbPost(postModel: PostModel): PostDbModel
}