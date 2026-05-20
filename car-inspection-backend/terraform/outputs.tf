output "bucket_name" {
  value = aws_s3_bucket.car_images_bucket.bucket
}

output "bucket_arn" {
  value = aws_s3_bucket.car_images_bucket.arn
}

output "backend_ecr_url" {
  value = aws_ecr_repository.backend_repo.repository_url
}

output "frontend_ecr_url" {
  value = aws_ecr_repository.frontend_repo.repository_url
}