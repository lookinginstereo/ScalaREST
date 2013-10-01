package com.zensey

/*
  For convenience this is just copy-pasta, but this would be built by the main scala project
  and imported in a jar with all(I think) of the stuff from
 */

case class QueryPost(
  deviceEntry: Boolean = true,
  manualEntry: Boolean = true,
  team: Boolean = true,
  individual: Boolean = true,
  includeInstance: Boolean = false,
  joinedBy: Option[String] = None,
  notJoinedBy: Option[String] = None,
  difficultyLevels: Option[Seq[String]] = None,
  categories: Option[Seq[String]] = None,
  skip: Option[Int] = None,
  limit: Option[Int] = None,
  section: String = "goals" //GoalSection.Goals.toString
  )
