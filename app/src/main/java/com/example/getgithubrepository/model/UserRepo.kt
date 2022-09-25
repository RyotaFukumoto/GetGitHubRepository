package com.example.getgithubrepository.model

import com.google.gson.annotations.SerializedName

data class UserRepo(
    @SerializedName("id")
    var id: Int,
    @SerializedName("node_id")
    var node_id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("full_name")
    var full_name: String,
    @SerializedName("owner")
    var owner: UserListData,
    @SerializedName("private")
    var private: Boolean,
    @SerializedName("html_url")
    var html_url: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("fork")
    var fork: Boolean,
    @SerializedName("url")
    var url: String,
    @SerializedName("archive_url")
    var archive_url: String,
    @SerializedName("assignees_url")
    var assignees_url: String,
    @SerializedName("blobs_url")
    var blobs_url: String,
    @SerializedName("branches_url")
    var branches_url: String,
    @SerializedName("collaborators_url")
    var collaborators_url: String,
    @SerializedName("comments_url")
    var comments_url: String,
    @SerializedName("commits_url")
    var commits_url: String,
    @SerializedName("compare_url")
    var compare_url: String,
    @SerializedName("contents_url")
    var contents_url: String,
    @SerializedName("contributors_url")
    var contributors_url: String,
    @SerializedName("deployments_url")
    var deployments_url: String,
    @SerializedName("downloads_url")
    var downloads_url: String,
    @SerializedName("events_url")
    var events_url: String,
    @SerializedName("forks_url")
    var forks_url: String,
    @SerializedName("git_commits_url")
    var git_commits_url: String,
    @SerializedName("git_refs_url")
    var git_refs_url: String,
    @SerializedName("git_tags_url")
    var git_tags_url: String,
    @SerializedName("git_url")
    var git_url: String,
    @SerializedName("issue_comment_url")
    var issue_comment_url: String,
    @SerializedName("issue_events_url")
    var issue_events_url: String,
    @SerializedName("issues_url")
    var issues_url: String,
    @SerializedName("keys_url")
    var keys_url: String,
    @SerializedName("labels_url")
    var labels_url: String,
    @SerializedName("languages_url")
    var languages_url: String,
    @SerializedName("merges_url")
    var merges_url: String,
    @SerializedName("milestones_url")
    var milestones_url: String,
    @SerializedName("notifications_url")
    var notifications_url: String,
    @SerializedName("pulls_url")
    var pulls_url: String,
    @SerializedName("releases_url")
    var releases_url: String,
    @SerializedName("ssh_url")
    var ssh_url: String,
    @SerializedName("stargazers_url")
    var stargazers_url: String,
    @SerializedName("statuses_url")
    var statuses_url: String,
    @SerializedName("subscribers_url")
    var subscribers_url: String,
    @SerializedName("subscription_url")
    var subscription_url: String,
    @SerializedName("tags_url")
    var tags_url: String,
    @SerializedName("teams_url")
    var teams_url: String,
    @SerializedName("trees_url")
    var trees_url: String,
    @SerializedName("clone_url")
    var clone_url: String,
    @SerializedName("mirror_url")
    var mirror_url: String,
    @SerializedName("hooks_url")
    var hooks_url: String,
    @SerializedName("svn_url")
    var svn_url: String,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("language")
    var language: String,
    @SerializedName("forks_count")
    var forks_count: Int,
    @SerializedName("stargazers_count")
    var stargazers_count: Int,
    @SerializedName("watchers_count")
    var watchers_count: Int,
    @SerializedName("size")
    var size: Int,
    @SerializedName("default_branch")
    var default_branch: String,
    @SerializedName("open_issues_count")
    var open_issues_count: Int,
    @SerializedName("is_template")
    var is_template: Boolean,
    @SerializedName("topics")
    var topics: List<String>,
    @SerializedName("has_issues")
    var has_issues: Boolean,
    @SerializedName("has_projects")
    var has_projects: Boolean,
    @SerializedName("has_wiki")
    var has_wiki: Boolean,
    @SerializedName("has_pages")
    var has_pages: Boolean,
    @SerializedName("has_downloads")
    var has_downloads: Boolean,
    @SerializedName("archived")
    var archived: Boolean,
    @SerializedName("disabled")
    var disabled: Boolean,
    @SerializedName("visibility")
    var visibility: String,
    @SerializedName("pushed_at")
    var pushed_at: String,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("updated_at")
    var updated_at: String,
//var permissions: { admin: false, push: false, pull: true },
    @SerializedName("emplate_repository")
    var emplate_repository: String
)
